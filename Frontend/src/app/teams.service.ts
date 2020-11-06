import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Teams} from './teams'
@Injectable({
  providedIn: 'root'
})
export class TeamsService {
  url="http://localhost:8080/team/all"
  constructor(private http:HttpClient) { }
  getTeams():Observable<Teams[]>{
    return this.http.get<Teams[]>(this.url)
  }
}
