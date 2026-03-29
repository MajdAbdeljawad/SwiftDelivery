import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-dashboard',
  imports: [CommonModule],
  template: `
    <section class="dashboard-placeholder">
      <h1>Dashboard</h1>
      <p>You are logged in and your authentication flow is connected through API Gateway.</p>
    </section>
  `,
  styles: [
    `
      .dashboard-placeholder {
        max-inline-size: 860px;
        margin: 2rem auto;
        padding: 2rem;
        border-radius: 12px;
        background: #ffffff;
        border: 1px solid #e5e7eb;
      }

      .dashboard-placeholder h1 {
        margin: 0 0 0.75rem;
      }

      .dashboard-placeholder p {
        margin: 0;
        color: #374151;
      }
    `
  ]
})
export class DashboardComponent {}