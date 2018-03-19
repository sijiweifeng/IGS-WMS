import { Component, ViewChild } from '@angular/core';
import { Nav, Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
import {WorkflowcontrolPage} from '../pages/core/workflowcontrol/workflowcontrol';

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;

  rootPage: any = HomePage;


  pages: Array<{title: string, component: any,name:string}>;

  constructor(public platform: Platform, public statusBar: StatusBar, public splashScreen: SplashScreen) {
    this.initializeApp();

    // used for an example of ngFor and navigation
    this.pages = [
    { title: 'Home', component: HomePage,name: "ion-ios-home-outline"},
    {title: 'Initiate WorkFlow', component: WorkflowcontrolPage,name:"ion-ios-star-outline"},
    
  ];

}


initializeApp() {
  this.platform.ready().then(() => {
    // Okay, so the platform is ready and our plugins are available.
    // Here you can do any higher level native things you might need.
    this.statusBar.styleDefault();
    this.splashScreen.hide();
  });
}

openPage(page) {
  // Reset the content nav to have just this page
  // we wouldn't want the back button to show in this scenario
  this.nav.setRoot(page.component);
}
}
