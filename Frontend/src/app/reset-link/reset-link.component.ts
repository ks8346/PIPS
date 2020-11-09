import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-reset-link',
  templateUrl: './reset-link.component.html',
  styleUrls: ['./reset-link.component.css']
})
export class ResetLinkComponent implements OnInit {

  resetLinkForm: FormGroup

  constructor() { }

  ngOnInit(): void {
    this.resetLinkForm = new FormGroup({
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required])
    });
  }

  onSubmit(){

  }

}
