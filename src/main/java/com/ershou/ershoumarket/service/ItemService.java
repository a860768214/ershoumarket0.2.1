package com.ershou.ershoumarket.service;

import com.ershou.ershoumarket.service.model.ItemModel;

import java.util.List;

public interface ItemService
{
//    创建商品
    ItemModel createItem(ItemModel itemModel);
//    列表浏览
    List<ItemModel> listItem();
//    商品详情浏览
    ItemModel getItemById(Integer id);
}
