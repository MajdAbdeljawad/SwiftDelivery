import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form = {
    email: '',
    password: '',
    rememberMe: false
  };

  onSubmit(): void {
    alert(`Demo login for ${this.form.email || 'user'}`);
  }
}
