
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({providedIn: 'root'})
export class TransferDataService {
userData: any;

/**
 * This method stores data to transfer data between components.
 * @param data 
 */
  emitData(data) {
    this.userData = data;
  }
}
