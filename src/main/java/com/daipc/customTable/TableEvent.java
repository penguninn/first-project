/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.customTable;

/**
 *
 * @author DaiPc
 */
public interface TableEvent {
    void onDelete(int row);
    void onUpdate(int row);
    void quantity(int qty);
}
