import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { User } from '../../models/user.model';
import { map, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private api: ApiService) {}

  loginAsync(email: string, password: string): Observable<User> {
    return this.api.get<User[]>('/users').pipe(
      map((users) => {
        const user = users.find((u) => u.email === email && u.password === password);
        if (!user) {
          throw new Error('Identifiants incorrects');
        }
        localStorage.setItem('currentUser', JSON.stringify(user));
        return user;
      })
    );
  }

  register(username: string, email: string, password: string): Observable<User> {
    return this.api.post<User>('/users', { username, email, password }).pipe(
      map((user) => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        return user;
      })
    );
  }

  getCurrentUser(): User | null {
    const raw = localStorage.getItem('currentUser');
    if (!raw) {
      return null;
    }
    return JSON.parse(raw) as User;
  }

  logout(): void {
    localStorage.removeItem('currentUser');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('currentUser');
  }
}
