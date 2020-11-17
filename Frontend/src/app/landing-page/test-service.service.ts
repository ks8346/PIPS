
import { Injectable } from '@angular/core';
import {FormGroup, FormControl,Validators, FormArray} from '@angular/forms';
/** This is the form Group of component */
@Injectable({
  providedIn: 'root'
})
export class TestServiceService {
  /**
   * This is the Constructor for testService
  */
  constructor() { }
  /**@ignore */
  form: FormGroup = new FormGroup({
  /**This is the key of form */ key: new FormControl(null),
  /**This is the form title */  title: new FormControl(' ',[Validators.required]),
  /**This is the Description */  description: new FormControl(' ',[Validators.required,Validators.maxLength(300)]),
  /**This is the teams array */  teams: new FormControl(),
  });
  
  /**@ignore */
  initialiseFormGroup(){
    this.form.setValue({
      $key: null,
      title: ' ',
      Proposal:' ',
      team: 0})
  }
}
