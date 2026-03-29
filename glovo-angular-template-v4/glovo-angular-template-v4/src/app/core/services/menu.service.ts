import { Injectable } from '@angular/core';
import { MenuItem } from '../models/menu-item.model';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private readonly items: MenuItem[] = [
    {
      id: 1,
      restaurantId: 1,
      name: 'Margherita Pizza',
      description: 'Tomato sauce, mozzarella, basil.',
      price: 18,
      image: 'https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=900&q=80',
      category: 'Pizza'
    },
    {
      id: 2,
      restaurantId: 1,
      name: 'Pepperoni Pizza',
      description: 'Cheese pizza with pepperoni slices.',
      price: 22,
      image: 'https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=900&q=80',
      category: 'Pizza'
    },
    {
      id: 3,
      restaurantId: 2,
      name: 'Classic Burger',
      description: 'Beef patty, lettuce, tomato, cheddar.',
      price: 16,
      image: 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=900&q=80',
      category: 'Burgers'
    },
    {
      id: 4,
      restaurantId: 2,
      name: 'Double Burger',
      description: 'Double beef, cheese, crispy onions.',
      price: 21,
      image: 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=900&q=80',
      category: 'Burgers'
    },
    {
      id: 5,
      restaurantId: 3,
      name: 'Salmon Roll',
      description: 'Fresh salmon with avocado and rice.',
      price: 24,
      image: 'https://images.unsplash.com/photo-1579871494447-9811cf80d66c?auto=format&fit=crop&w=900&q=80',
      category: 'Sushi'
    },
    {
      id: 6,
      restaurantId: 4,
      name: 'Chocolate Waffle',
      description: 'Belgian waffle with melted chocolate.',
      price: 14,
      image: 'https://images.unsplash.com/photo-1551024506-0bccd828d307?auto=format&fit=crop&w=900&q=80',
      category: 'Desserts'
    }
  ];

  getAll(): MenuItem[] {
    return this.items;
  }

  getByRestaurant(restaurantId: number): MenuItem[] {
    return this.items.filter((item) => item.restaurantId === restaurantId);
  }
}
