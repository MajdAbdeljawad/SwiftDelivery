import { Component } from '@angular/core';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent {
  profile = {
    fullName: 'Firas Toukabri',
    email: 'firas@example.com',
    phone: '+216 12 345 678',
    address: 'Sousse, Tunisia'
  };

  save(): void {
    alert('Demo profile updated.');
  }
}
