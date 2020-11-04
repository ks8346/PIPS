import { getLocaleDateFormat } from '@angular/common';
import { Component, OnInit,Input,Output,EventEmitter, ɵExtraLocaleDataIndex } from '@angular/core';
import { MatStartDate } from '@angular/material/datepicker';
import { Post } from 'src/app/post';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})

export class FilterComponent implements OnInit {
  @Input()
  public sDate
  public newEdate
  public eDate
  public data:Date[]
  public typePost="teamPost";
  

 
 
  

 @Output() 
 parentFunctionFilter:EventEmitter<any> = new EventEmitter<any>();
 public message;

  constructor() { 
  
  }

  ngOnInit(): void {
  
  }
  processFilter()
  { 
    //  eeDate:Date = this.eDate;
    //  eeDate

    //  yourDate.setDate(yourDate.getDate() + 1);
    this.newEdate = this.eDate;
    this.newEdate = this.newEdate.setDate(this.newEdate.getDate()+1);
    this.data = [this.sDate,this.newEdate];
    
    if(this.sDate==null || this.eDate==null)
    this.message = "Please select both Start date and End date !!"
    else
    if(this.sDate>this.eDate)
    this.message = "Start Date can not be Greater then End Date"
    else
    {
     this.sendData(this.data);
     this.message=""
    }


   
  }

  selectionChanged()
  { 
    
     this.sendData(this.typePost);
  }

  sendData(data)
  {
         this.parentFunctionFilter.emit(data) 
  }

}
