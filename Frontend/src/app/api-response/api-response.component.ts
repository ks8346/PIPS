import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Component, OnInit, Inject } from '@angular/core';

/**This is a dialog box component */
@Component({
  selector: 'app-api-response',
  templateUrl: './api-response.component.html',
  styleUrls: ['./api-response.component.css']
})
export class ApiResponseComponent {

  /**message to be displayed in the dialog box */
  msg:any

  /**@ignore */
  islogin:boolean=true;

  /**@constructor */
  constructor(
    /**@ignore */
    public dialogRef: MatDialogRef<ApiResponseComponent>, @Inject(MAT_DIALOG_DATA) public data) {
      this.msg=data.data 
    }

 /** This method closes the Dialog Box on submission*/
  onNoClick(): void {
    this.dialogRef.close(true);
  }
}
