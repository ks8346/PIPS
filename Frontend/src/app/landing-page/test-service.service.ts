
import { Injectable } from '@angular/core';
import {FormGroup, FormControl,Validators, FormArray} from '@angular/forms';
@Injectable({
  providedIn: 'root'
})
export class TestServiceService {

  constructor() { }
  form: FormGroup = new FormGroup({
    key: new FormControl(null),
    title: new FormControl(' ',[Validators.required,Validators.minLength(5)]),
    description: new FormControl(' ',[Validators.required,Validators.maxLength(500)]),
    teams: new FormControl([]),
  });
  
  initialiseFormGroup(){
    this.form.setValue({
      $key: null,
      title: '',
      Proposal:'',
      teams: []})
  }
}
