package com.tracker.inventory.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracker.inventory.domain.Item;
import com.tracker.inventory.domain.ItemRepository;


@Service //it tell us that it has to be instanciated
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void addNewItem(Item item) {

        Optional<Item> itemOptional = itemRepository.findItemByName(item.getName());

        if (itemOptional.isPresent()) {
            throw new IllegalStateException("name already taken");
        }
        itemRepository.save(item);


    }


    public void deleteItem(UUID itemId) {
        boolean exists = itemRepository.existsById(itemId);
        if (!exists) {
            throw new IllegalStateException("item with id" + itemId + "does not exists");
        }
        itemRepository.deleteById(itemId);
    }

    //@Transactional
    public void updateItem(UUID itemId, Item modifiedItem) {

        //TODO:Validation
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("id"));
        item.setLocation(modifiedItem.getLocation());
        item.setQuantity(modifiedItem.getQuantity());
        item.setName(modifiedItem.getName());
        if (modifiedItem.getDeletionMessage() != null) {
            item.setDeletionMessage(modifiedItem.getDeletionMessage());
        }
        item.setIsDeleted(modifiedItem.getIsDeleted());
        itemRepository.save(item);
    }

    public Item getItem(UUID itemId) {

        boolean exists = itemRepository.existsById(itemId);
        if (!exists) {
            throw new IllegalStateException("item with id" + itemId + "does not exists");
        }

        return itemRepository.findById(itemId).get();
    }

    public List<Item> findAllFilter(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("is_deleted", isDeleted);
        List<Item> items = itemRepository.findAll();
        session.disableFilter("deletedItemFilter");
        return items;
    }
}
