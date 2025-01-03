import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Hotel } from '../model/hotel.model';

@Injectable({
  providedIn: 'root'
})
export class HotelserviceService {

  baseUrl: string = "http://localhost:8089/api/hotel/";

  constructor(
    private httpClient: HttpClient
  ) { }


  getAllHotel(): Observable<any> {

    return this.httpClient.get(this.baseUrl)
      .pipe(
        catchError(this.handleError)
      );

  }

  createHotel(hotel:Hotel, image:File): Observable<Hotel>{

    const formData=new FormData();
        formData.append('hotel', new Blob([JSON.stringify(hotel)], {type:'application/json'}));
        formData.append('image', image);   
    
    
        return this.httpClient.post<Hotel>(this.baseUrl+'save', formData)
        .pipe(
          catchError(this.handleError)
        );

  }




  private handleError(error: any) {

    console.error("An Error occured: ", error);
    return throwError(() => new Error(error.message || 'Srver Error'));
  }

}
