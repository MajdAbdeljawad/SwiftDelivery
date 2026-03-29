import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../../core/models/restaurant.model';
import { RestaurantService } from '../../core/services/restaurant.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  restaurants: Restaurant[] = [];
  filteredRestaurants: Restaurant[] = [];
  categories = ['Pizza', 'Burgers', 'Sushi', 'Desserts', 'Healthy'];
  highlights = [
    { title: 'Live order tracking', text: 'Follow each delivery step in real time.' },
    { title: 'Favorites', text: 'Save restaurants and reorder quickly.' },
    { title: 'Checkout flow', text: 'Ready UI for address and payment method.' }
  ];

  constructor(private readonly restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.restaurants = this.restaurantService.getRestaurants();
    this.filteredRestaurants = [...this.restaurants];
  }

  onSearch(query: string): void {
    if (!query) {
      this.filteredRestaurants = [...this.restaurants];
      return;
    }

    const q = query.toLowerCase();
    this.filteredRestaurants = this.restaurants.filter((restaurant) =>
      restaurant.name.toLowerCase().includes(q) ||
      restaurant.category.toLowerCase().includes(q) ||
      restaurant.description.toLowerCase().includes(q)
    );
  }
}
