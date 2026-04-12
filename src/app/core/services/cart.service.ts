import { Injectable } from '@angular/core';
import { CartItem } from '../models/cart-item.model';
import { MenuItem } from '../models/menu-item.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  items: CartItem[] = [];

  add(item: MenuItem): void {
    const existing = this.items.find((entry) => entry.item.id === item.id);
    if (existing) {
      existing.quantity += 1;
      return;
    }

    this.items = [...this.items, { item, quantity: 1 }];
  }

  increase(itemId: number): void {
    this.items = this.items.map((entry) =>
      entry.item.id === itemId ? { ...entry, quantity: entry.quantity + 1 } : entry
    );
  }

  decrease(itemId: number): void {
    this.items = this.items
      .map((entry) =>
        entry.item.id === itemId ? { ...entry, quantity: entry.quantity - 1 } : entry
      )
      .filter((entry) => entry.quantity > 0);
  }

  remove(itemId: number): void {
    this.items = this.items.filter((entry) => entry.item.id !== itemId);
  }

  clear(): void {
    this.items = [];
  }

  getItems(): CartItem[] {
    return this.items;
  }

  getSubtotal(): number {
    return this.items.reduce((sum, entry) => sum + entry.item.price * entry.quantity, 0);
  }

  getDeliveryFee(): number {
    return this.items.length ? 7 : 0;
  }

  getTotal(): number {
    return this.getSubtotal() + this.getDeliveryFee();
  }
}
