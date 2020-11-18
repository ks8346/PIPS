import { Component, OnInit,Input,Output,EventEmitter} from '@angular/core';
  
/**
 * FilterComponent is the child component of the landing-page.
 */
@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})


export class FilterComponent implements OnInit {
  /**
   * @ignore
   */
  @Input()
  public sDate

  /**
   * @ignore
   */
  public newEdate

  /**
   * @ignore
   */
  public eDate

  /**
   * @ignore
   */
  public data:Date[]

  /**
   * @ignore
   */
  public typePost="teamPost";
  

 
 
  
  /**
   * This is the EventEmitter emits to Landing-page
   */
 @Output() 
 parentFunctionFilter:EventEmitter<any> = new EventEmitter<any>();

 /**
   * @ignore
   */ 
 public message;
  /**
   * @ignore
   */ 
  constructor() { 
  
  }
  /**
   * @ignore
   */
  ngOnInit(): void {
  
  }

  /**
   * This function is resposible for adding a day to a end Date user selected
   */
  datePlusOne(){
    console.log("date",this.eDate)
    this.newEdate = this.eDate;
    this.newEdate = this.newEdate.setDate(this.newEdate.getDate()+1);
  }

  /**
   * This function is resposible for the Validations applied on the start date and end date.
   */
  processFilter()
  { 
    
    this.data = [this.sDate,this.newEdate];
    
    if(this.sDate==null || this.eDate==null)
    this.message = "Please select both Start date and End date !!"
    else
    if(this.sDate>=this.eDate)
    this.message = "Start Date can not be Greater then End Date"
    else
    {
     this.sendData(this.data);
     this.message=""
    }


   
  }

  /**
   * As user changed the seletion on the select post this function send the data to sendDate method.
   */
  selectionChanged()
  { 
    
     this.sendData(this.typePost);
  }
  /**
   * As user changed the seletion on the select post this method emit the information to the landing-page
   * and landing-page calls the api
   */
  sendData(data)
  {
         this.parentFunctionFilter.emit(data) 
  }

}
