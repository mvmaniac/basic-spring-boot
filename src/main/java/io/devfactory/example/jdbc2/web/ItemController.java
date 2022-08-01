package io.devfactory.example.jdbc2.web;


import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;
import io.devfactory.example.jdbc2.service.ItemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

  private final ItemService itemService;

  public ItemController(@Qualifier("jpaItemServiceV3") ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping
  public String items(@ModelAttribute("itemSearch") ItemSearchCond itemSearch, Model model) {
    List<Item> items = itemService.findItems(itemSearch);
    model.addAttribute("items", items);
    return "items";
  }

  @GetMapping("/{itemId}")
  public String item(@PathVariable long itemId, Model model) {
    Item item = itemService.findById(itemId).orElseThrow(IllegalArgumentException::new);
    model.addAttribute("item", item);
    return "item";
  }

  @GetMapping("/add")
  public String addForm() {
    return "addForm";
  }

  @PostMapping("/add")
  public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
    Item savedItem = itemService.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/items/{itemId}";
  }

  @GetMapping("/{itemId}/edit")
  public String editForm(@PathVariable Long itemId, Model model) {
    Item item = itemService.findById(itemId).orElseThrow(IllegalArgumentException::new);
    model.addAttribute("item", item);
    return "editForm";
  }

  @PostMapping("/{itemId}/edit")
  public String edit(@PathVariable Long itemId, @ModelAttribute ItemUpdateDto updateParam) {
    itemService.update(itemId, updateParam);
    return "redirect:/items/{itemId}";
  }

}
