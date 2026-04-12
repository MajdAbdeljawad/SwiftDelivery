import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../../../core/models/restaurant.model';
import { RestaurantService } from '../../../core/services/restaurant.service';

@Component({
  selector: 'app-restaurants-list',
  templateUrl: './restaurants-list.component.html',
  styleUrls: ['./restaurants-list.component.css']
})
export class RestaurantsListComponent implements OnInit {
  restaurants: Restaurant[] = [];
  filteredRestaurants: Restaurant[] = [];
  query = '';
  selectedCategory = 'All';
  categories = ['All', 'Pizza', 'Burgers', 'Sushi', 'Desserts'];

  constructor(private readonly restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.restaurants = this.restaurantService.getRestaurants();
    this.applyFilters();
  }

  applyFilters(): void {
    const q = this.query.toLowerCase();
    this.filteredRestaurants = this.restaurants.filter((restaurant) => {
      const matchesQuery = !q ||
        restaurant.name.toLowerCase().includes(q) ||
        restaurant.description.toLowerCase().includes(q);
      const matchesCategory = this.selectedCategory === 'All' || restaurant.category === this.selectedCategory;
      return matchesQuery && matchesCategory;
    });
  }
}
