import { Component } from '@angular/core';

type Kpi = { label: string; value: string; delta: string; trend: 'up' | 'down' };
type QuickStat = { label: string; value: string; note: string };

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent {
  readonly weekDays = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];

  kpis: Kpi[] = [
    { label: 'Utilisateurs actifs', value: '12 480', delta: '+8.4%', trend: 'up' },
    { label: 'Restaurants partenaires', value: '164', delta: '+5.1%', trend: 'up' },
    { label: 'Produits en ligne', value: '3 260', delta: '+11.9%', trend: 'up' },
    { label: 'Commandes du jour', value: '842', delta: '-2.6%', trend: 'down' }
  ];

  quickStats: QuickStat[] = [
    { label: 'Panier moyen', value: '42.7 TND', note: '+3.2% vs semaine derniere' },
    { label: 'Temps livraison moyen', value: '24 min', note: '-1.8 min vs objectif' },
    { label: 'Taux annulation', value: '1.9%', note: 'stable sur 30 jours' },
    { label: 'Clients recurrents', value: '67%', note: '+4.5 pts ce mois' }
  ];

  readonly revenueSeries = [12800, 14900, 16250, 15400, 17800, 19600, 18850];
  readonly ordersSeries = [640, 688, 702, 671, 739, 812, 780];
  readonly deliverySeries = [21, 22, 24, 23, 26, 28, 25];

  readonly revenueCurve = this.buildLinePoints(this.revenueSeries);
  readonly ordersCurve = this.buildLinePoints(this.ordersSeries);
  readonly deliveryCurve = this.buildLinePoints(this.deliverySeries);

  recentOrders = [
    { id: '#CMD-2401', customer: 'Sarra Ben Amor', total: '48 TND', status: 'En préparation' },
    { id: '#CMD-2402', customer: 'Amine Trabelsi', total: '31 TND', status: 'En livraison' },
    { id: '#CMD-2403', customer: 'Yassine Jaziri', total: '66 TND', status: 'Payée' }
  ];

  private buildLinePoints(series: number[]): string {
    const width = 340;
    const height = 160;
    const padX = 18;
    const padY = 16;
    const max = Math.max(...series);
    const min = Math.min(...series);
    const range = Math.max(max - min, 1);
    const stepX = (width - padX * 2) / Math.max(series.length - 1, 1);

    return series
      .map((value, index) => {
        const x = padX + stepX * index;
        const y = height - padY - ((value - min) / range) * (height - padY * 2);
        return `${x},${y}`;
      })
      .join(' ');
  }
}
