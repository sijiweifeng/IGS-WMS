import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import{RequestPage} from'../Receipt/Garment/request/request';
import{InitGarmentPage} from '../Receipt/Garment/init-garment/init-garment';
import{FabricRequestPage} from'../Receipt/Fabric/fabricRequest/fabricRequest';
import {TranslateService} from '@ngx-translate/core';
import {QaissuePage} from "../../issue/qaissue/qaissue";
import {RequestissuePage} from "../../issue/requestissue/requestissue";
import {FnissuePage} from "../../issue/fnissue/fnissue";

/**
* Generated class for the StartWorkflowPage page.
*
* See https://ionicframework.com/docs/components/#navigation for more info on
* Ionic pages and navigation.
*/

@IonicPage()
@Component({
  selector: 'page-start-workflow',
  templateUrl: 'start-workflow.html',
})
export class StartWorkflowPage {
  param = {value: 'world'};
  GarmentRequest:any;
  FabricRequest:any;
  RequestQAPage:any;
  RequestFNPage:any;
  RequsetPage:any;
  Setting:any;
  leavePage:any;
  showIssueCatbool:boolean;
  showGarmnetCatbool:boolean;

  constructor(public navCtrl: NavController, public navParams: NavParams,translate:TranslateService)
  {
    this.GarmentRequest = RequestPage;
    this.FabricRequest = FabricRequestPage;
    this.RequestQAPage = QaissuePage;
    this.RequestFNPage=FnissuePage;
    this.RequsetPage= RequestissuePage;
    this.showGarmnetCatbool = false;

    translate.setTranslation('cn', {
      Garment: '中文'
    });
    translate.use('en');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad StartWorkflowPage');
  }

  toggleTranslate(language:string){

  }
  showIssueCat(){
    this.showIssueCatbool = !this.showIssueCatbool;

  }

  showGarmnetCat(){
    this.showGarmnetCatbool = !this.showGarmnetCatbool;
  }

  doGarmentRequest(requestType:string){
    this.navCtrl.push(InitGarmentPage,{reqType:requestType});
  }

  HidetabBar(){
    let elements = document.querySelectorAll(".tabbar");
    if (elements != null) {
      Object.keys(elements).map((key) => {
        elements[key].style.display = 'none';
      });
    }

  }
}
