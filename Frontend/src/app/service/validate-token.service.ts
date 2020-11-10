import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class ValidateTokenService {

  constructor(private http: HttpClient) { }

  tokenVerification(data){
    console.log("inside tokenVerification")
    return this.http.post("http://localhost:8080/validate_token",
    data["data1"],{responseType:'text' as 'json'})
  }
}

// $2a$10$liD/Pd76Qod0CtiFE1nfg.ruHB56kRGFxOCQTEaJk24oayaBl
