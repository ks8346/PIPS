import { Component, OnInit,HostListener } from '@angular/core';
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
  innerWidth;

  /**@ignore */
  show:boolean=true

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

    this.resize()
  }

   /**
 * This method is called by OnResize() and sets values of different variables that controls the padding and width
 */
resize(){
  if(this.innerWidth<916){
    this.show=false
    
  }
  else{
    this.show=true
    
  }
}

/**window resize event handled*/
@HostListener('window:resize', ['$event'])

onResize(event) {
  this.innerWidth = event.target.innerWidth;
  this.resize()
}
  

}
