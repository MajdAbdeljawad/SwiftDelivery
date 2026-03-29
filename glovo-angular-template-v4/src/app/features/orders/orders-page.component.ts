import { Component } from '@angular/core';

@Component({
  selector: 'app-orders-page',
  templateUrl: './orders-page.component.html',
  styleUrls: ['./orders-page.component.css']
})
export class OrdersPageComponent {
  orders = [
    { id: '#1024', restaurant: 'Pizza Town', date: 'Today - 20:10', status: 'On the way', total: '28 DT' },
    { id: '#1019', restaurant: 'Sushi Box', date: 'Yesterday - 13:00', status: 'Delivered', total: '41 DT' },
    { id: '#1011', restaurant: 'Burger House', date: '20 Mar - 19:30', status: 'Delivered', total: '22 DT' }
  ];
}
