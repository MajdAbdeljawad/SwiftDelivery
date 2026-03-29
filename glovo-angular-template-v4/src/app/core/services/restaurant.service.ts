import { Injectable } from '@angular/core';
import { Restaurant } from '../models/restaurant.model';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {
  private readonly restaurants: Restaurant[] = [
    {
      id: 1,
      name: 'Pizza Town',
      image: 'https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=900&q=80',
      category: 'Pizza',
      rating: 4.6,
      deliveryTime: '25 min',
      deliveryFee: '2.5 DT',
      description: 'Fresh pizza, cheesy slices, and fast delivery.'
    },
    {
      id: 2,
      name: 'Burger House',
      image: 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=900&q=80',
      category: 'Burgers',
      rating: 4.4,
      deliveryTime: '20 min',
      deliveryFee: '3 DT',
      description: 'Smash burgers, fries, and signature sauces.'
    },
    {
      id: 3,
      name: 'Sushi Box',
      image: 'https://images.unsplash.com/photo-1579871494447-9811cf80d66c?auto=format&fit=crop&w=900&q=80',
      category: 'Sushi',
      rating: 4.8,
      deliveryTime: '30 min',
      deliveryFee: '4 DT',
      description: 'Premium sushi rolls and healthy bowls.'
    },
    {
      id: 4,
      name: 'Sweet Corner',
      image: 'https://images.unsplash.com/photo-1551024506-0bccd828d307?auto=format&fit=crop&w=900&q=80',
      category: 'Desserts',
      rating: 4.7,
      deliveryTime: '18 min',
      deliveryFee: '2 DT',
      description: 'Cakes, waffles, donuts, and sweet drinks.'
    }
  ];

  getRestaurants(): Restaurant[] {
    return this.restaurants;
  }
}
