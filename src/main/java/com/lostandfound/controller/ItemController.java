package com.lostandfound.controller;

import com.lostandfound.ErrorHandling.ResourceNotFoundException;
import com.lostandfound.model.Item;
import com.lostandfound.model.User;
import com.lostandfound.repository.ItemRepository;
import com.lostandfound.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Item not found with ID: " + id));
    }

    // Add a new lost or found item
    @PostMapping
    public Item addItem(@RequestBody Item item) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

        User user = userRepository.findByUsername(username);

        item.setDateReported(LocalDate.now());
        item.setUser(user);

        // Validate location fields based on status
        if ("LOST".equalsIgnoreCase(String.valueOf(item.getStatus())) && (item.getLocationLost() == null || item.getLocationLost().isBlank())) {
            throw new IllegalArgumentException("Location lost must be provided for LOST items.");
        }
        if ("FOUND".equalsIgnoreCase(String.valueOf(item.getStatus())) && (item.getLocationFound() == null || item.getLocationFound().isBlank())) {
            throw new IllegalArgumentException("Location found must be provided for FOUND items.");
        }


        return itemRepository.save(item);
    }
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        // Find the existing item
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));

        // Update fields if provided
        existingItem.setTitle(updatedItem.getTitle() != null ? updatedItem.getTitle() : existingItem.getTitle());
        existingItem.setDescription(updatedItem.getDescription() != null ? updatedItem.getDescription() : existingItem.getDescription());
        existingItem.setStatus(updatedItem.getStatus() != null ? updatedItem.getStatus() : existingItem.getStatus());
        existingItem.setLocationFound(updatedItem.getLocationFound() != null ? updatedItem.getLocationFound() : existingItem.getLocationFound());
        existingItem.setLocationLost(updatedItem.getLocationLost() != null ? updatedItem.getLocationLost() : existingItem.getLocationLost());

        // Validate status-specific fields
        if ("LOST".equalsIgnoreCase(String.valueOf(existingItem.getStatus())) && (existingItem.getLocationLost() == null || existingItem.getLocationLost().isBlank())) {
            throw new IllegalArgumentException("Location lost must be provided for LOST items.");
        }
        if ("FOUND".equalsIgnoreCase(String.valueOf(existingItem.getStatus())) && (existingItem.getLocationFound() == null || existingItem.getLocationFound().isBlank())) {
            throw new IllegalArgumentException("Location found must be provided for FOUND items.");
        }

        // Save and return updated item
        return itemRepository.save(existingItem);
    }
    @PatchMapping("/{id}")
    public Item updateItemPartially(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + id));

        // Update fields dynamically
        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    item.setTitle((String) value);
                    break;
                case "description":
                    item.setDescription((String) value);
                    break;
                case "status":
                    item.setStatus(Item.Status.valueOf(value.toString().toUpperCase()));
                    break;
                case "locationFound":
                    item.setLocationFound((String) value);
                    break;
                case "locationLost":
                    item.setLocationLost((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        return itemRepository.save(item);
    }

    // Get all items
    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));


        itemRepository.delete(item);

        return "Item with id " + id + " deleted successfully.";
    }

    // Get all lost or found items
    @GetMapping("/{status}")
    public List<Item> getItemsByStatus(@PathVariable String status) {
        return itemRepository.findByStatus(Item.Status.valueOf(status.toUpperCase()));
    }
}

