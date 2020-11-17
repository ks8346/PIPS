import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public islogin:boolean;
  public isregister:boolean;
  constructor(private activatedRoute:ActivatedRoute) { }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.paramMap.get('id'))
    {
      this.islogin=(this.activatedRoute.snapshot.paramMap.get('id')==='true')
    }
    
  }
  /**
   * This method makes the islogin flag true to make the Log In Component visible
   */
  goToLogIn(){
    this.islogin=true
  }

}
