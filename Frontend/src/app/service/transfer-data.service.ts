
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({providedIn: 'root'})
export class TransferDataService {
userData: any;
  //observer = new Subject();
//  public subscriber = this.observer.asObservable();

  emitData(data) {
    this.userData = data;
   // this.observer.next(data);
  }
}
