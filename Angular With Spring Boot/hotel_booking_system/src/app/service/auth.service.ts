import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { AuthResponse } from '../model/AuthResponse';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8089';
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  private userRoleSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

  public userRole$: Observable<string | null> = this.userRoleSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }


  register(
    user: {
      name: string;
      email: string;
      password: string;
      cell: string;
      address: string;
      dob: Date;
      gender: string;
      image: string
    }
  ): Observable<AuthResponse> {

    return this.http.post<AuthResponse>(this.baseUrl + '/register', user, { headers: this.headers })
      .pipe(

        map(
          (response: AuthResponse) => {
            if (this.isBrowser() && response.token) {
              localStorage.setItem('authToken', response.token);
            }
            return response;
          }

        )
      );


  }

  login(email: string, password: string): Observable<AuthResponse> {

    return this.http.post<AuthResponse>(this.baseUrl + '/login', { email, password }, { headers: this.headers }).pipe(

      map(
        (response: AuthResponse) => {
          if (this.isBrowser() && response.token) {
            localStorage.setItem('authToken', response.token);
            const decodeToken = this.decodeToken(response.token);
            localStorage.setItem('userRole', decodeToken.role);
            this.userRoleSubject.next(decodeToken.role);
          }
          return response;

        }

      )
    );
  }


  getToken(): string | null {

    if (this.isBrowser()) {
      return localStorage.getItem('authToken');
    }
    return null;

  }


  getUserRole(): string | null {

    if (this.isBrowser()) {
      return localStorage.getItem('userRole');
    }
    return null;

  }


  isAdmin(): boolean {
    return this.getUserRole() === 'ADMIN';
  }

  isAdminOrHotel(): boolean {
    const role = this.getUserRole();
    return role === 'ADMIN' || role === 'HOTEL';

  }

  isHotel(): boolean {
    return this.getUserRole() === 'HOTEL';
  }

  isUser(): boolean {
    return this.getUserRole() === 'USER';
  }


  isTokenExpired(token: string): boolean {
    const docodeToken = this.decodeToken(token);

    const expiry = docodeToken.exp * 1000;
    return Date.now() > expiry;
  }

  isLoggIn(): boolean {
    const token = this.getToken();
    if (token && !this.isTokenExpired(token)) {
      return true;
    }
    this.logout();
    return false;

  }

  logout():void{
    if (this.isBrowser()) {
       localStorage.removeItem('userRole');
       localStorage.removeItem('authToken');
       this.userRoleSubject.next(null);
    }
    this.router.navigate(['/login']);
  }

  hasRole(roles: string[]):boolean{

    const userRole=this.getUserRole();
    return userRole? roles.includes(userRole):false;

  }



  decodeToken(token: string) {

    const payload = token.split('.')[1];
    return JSON.parse(atob(payload));

  }

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }


}
