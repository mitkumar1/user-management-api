import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  loading = true;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    this.loading = true;
    this.authService.getUserProfile().subscribe({
      next: (user) => {
        this.user = user;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  refreshProfile(): void {
    this.loadProfile();
  }

  isAdmin(): boolean {
    return this.user?.roles?.some(role => role.name === 'ROLE_ADMIN') || false;
  }
}
