import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

/**This component is the home page of application */
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  /**Flag which is set true when user clicks on login button */
  public islogin:boolean;

  /** Flag which is set true when user clicks on register button*/
  public isregister:boolean;

  /**@ignore */
  constructor(
    /**@ignore */
    private activatedRoute:ActivatedRoute) { }

  /**@ignore */
  ngOnInit(): void {
    if (this.activatedRoute.snapshot.paramMap.get('id'))
    {
      this.islogin=(this.activatedRoute.snapshot.paramMap.get('id')==='true')
    } 
  }
  

}
