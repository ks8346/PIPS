import { Component, OnInit } from '@angular/core';

/**This component is responsible to handle incorrect URLs enetered by the user */
@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  /**message to be displayed in case of wrong url entered */
  errorMessage = "Some Error Occured"

  /**@ignore */
  constructor() { }

  /**@ignore */
  ngOnInit(): void {
  }

}
