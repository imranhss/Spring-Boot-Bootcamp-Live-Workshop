import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Location } from '../model/location.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  baseUrl: string="http://localhost:8089/api/location/"

  constructor(
    private httpClient:HttpClient
  ) { }


  // create hotel
  createLocation(locatin:Location, image:File):Observable<Location>{
    const formData=new FormData();
    formData.append('location', new Blob([JSON.stringify(locatin)], {type:'application/json'}));
    formData.append('image', image);   


    return this.httpClient.post<Location>(this.baseUrl+'save', formData);

  }



 // get all location
 getAllLocation(){

  return this.httpClient.get(this.baseUrl);
 }

  

}
