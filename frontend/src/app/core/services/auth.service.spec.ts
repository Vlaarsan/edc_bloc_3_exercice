import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { ApiService } from './api.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService, ApiService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('loginAsync should store the authenticated user', () => {
    const mockUser = { id: 1, username: 'alice', email: 'alice@test.com', password: 'pw' };
    service.loginAsync('alice@test.com', 'pw').subscribe((user) => {
      expect(user.username).toBe('alice');
      expect(localStorage.getItem('currentUser')).toBeTruthy();
    });
    const req = httpMock.expectOne('http://localhost:8080/users');
    expect(req.request.method).toBe('GET');
    req.flush([mockUser]);
  });

  it('loginAsync should throw on wrong credentials', () => {
    service.loginAsync('bad@test.com', 'pw').subscribe({
      error: (err) => expect(err.message).toBe('Identifiants incorrects')
    });
    httpMock.expectOne('http://localhost:8080/users').flush([]);
  });

  it('register should store the new user', () => {
    const mockUser = { id: 2, username: 'bob', email: 'bob@test.com' };
    service.register('bob', 'bob@test.com', 'pw').subscribe((user) => {
      expect(user.username).toBe('bob');
      expect(localStorage.getItem('currentUser')).toBeTruthy();
    });
    const req = httpMock.expectOne('/users');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);
  });

  it('getCurrentUser should return the stored user', () => {
    const user = { id: 1, username: 'alice', email: 'alice@test.com' };
    localStorage.setItem('currentUser', JSON.stringify(user));
    expect(service.getCurrentUser()?.username).toBe('alice');
  });

  it('getCurrentUser should return null when nothing stored', () => {
    expect(service.getCurrentUser()).toBeNull();
  });

  it('logout should remove the stored user', () => {
    localStorage.setItem('currentUser', JSON.stringify({ id: 1 }));
    service.logout();
    expect(localStorage.getItem('currentUser')).toBeNull();
  });

  it('isAuthenticated should reflect storage state', () => {
    expect(service.isAuthenticated()).toBeFalse();
    localStorage.setItem('currentUser', JSON.stringify({ id: 1 }));
    expect(service.isAuthenticated()).toBeTrue();
  });
});
