package com.lostandfound.controller;

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

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

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
        Item savedItem = itemRepository.save(item);

        return itemRepository.save(item);
    }

    // Get all items
    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Get all lost or found items
    @GetMapping("/{status}")
    public List<Item> getItemsByStatus(@PathVariable String status) {
        return itemRepository.findByStatus(Item.Status.valueOf(status.toUpperCase()));
    }
}

