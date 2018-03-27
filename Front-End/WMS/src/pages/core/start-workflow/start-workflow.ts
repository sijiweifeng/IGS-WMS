import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { RequestPage } from '../Receipt/Garment/request/request';
import { InitGarmentPage } from '../Receipt/Garment/init-garment/init-garment';
import { FabricRequestPage } from '../Receipt/Fabric/fabricRequest/fabricRequest';
import { TranslateService } from '@ngx-translate/core';
import { QaissuePage } from "../../issue/qaissue/qaissue";
import { RequestissuePage } from "../../issue/requestissue/requestissue";
import { FnissuePage } from "../../issue/fnissue/fnissue";
import { AppConfig } from "../../../app/app.config";
import { EsqHttpClient } from "../../../providers/HttpClient";
import { EsqCacheHelper } from "../../../providers/cacheHelper";
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

  GarmentRequest: any;
  FabricRequest: any;
  RequestQAPage: any;
  RequestFNPage: any;
  RequsetPage: any;
  Setting: any;
  leavePage: any;
  images: any;
  workFlowOption: any;
  showIssueCatbool: boolean;
  ButtonDivs: Array<any>;
  showGarmnetCatbool: boolean;
  factoryCode: string;

  constructor(public navCtrl: NavController, public httpclient: EsqHttpClient, public navParams: NavParams, translate: TranslateService, public esqCache: EsqCacheHelper) {

    this.images = [
      { src: "../../assets/imgs/garment-bg.jpg" },
      { src: "../../assets/imgs/fabric-bg.jpg" },
      { src: "../../assets/imgs/stock-bg.jpg" },
      { src: "../../assets/imgs/wt-bg.jpg" },
      { src: "../../assets/imgs/lt-bg.jpg" },
      { src: "../../assets/imgs/qa-bg.jpg" },
      { src: "../../assets/imgs/issue-bg.jpg" },
    ];

    this.esqCache.getKeyValue("factory").catch((data) => {
      console.log(data);
    }).then((data) => {
      if (data != undefined) {
        this.factoryCode = data.factoryCode;
        this.getBusinessTypeList();
      }
    });
    
    // this.workFlowOption =
    //   [
    //     {
    //       name: "Garment Receipt Request",
    //       subCategory: [
    //         { subcatName: "Normal", type: "Normal" },
    //         { subcatName: "QA", type: "QA" },
    //         { subcatName: "Out Source", type: "outSource" }
    //       ]
    //     },
    //     {
    //       name: "Fabric Receipt Request",
    //       subCategory: []
    //     },
    //     {
    //       name: "Stock Counting",
    //       subCategory: []
    //     },
    //     {
    //       name: "Warehouse Transfer",
    //       subCategory: []
    //     },
    //     {
    //       name: "Location Transfer",
    //       subCategory: []
    //     },
    //     {
    //       name: "Quality Assurance",
    //       subCategory: []
    //     },
    //     {
    //       name: "Issue",
    //       subCategory: [
    //         { subcatName: "QA" },
    //         { subcatName: "FN" },
    //         { subcatName: "Request" }
    //       ]
    //     }
    //   ];

    this.GarmentRequest = RequestPage;
    this.FabricRequest = FabricRequestPage;
    this.RequestQAPage = QaissuePage;
    this.RequestFNPage = FnissuePage;
    this.RequsetPage = RequestissuePage;
    this.showGarmnetCatbool = false;
    translate.setTranslation('cn', {
      Garment: '中文'
    });
    translate.use('en');

    var images = new Array
    images[0] = "../../assets/imgs/garment-bg.jpg",
      images[1] = "../../assets/imgs/garment-bg.jpg",
      images[2] = "../../assets/imgs/fabric-bg.jpg",
      images[3] = "../../assets/imgs/wt-bg.jpg",
      images[4] = "../../assets/imgs/lt-bg.jpg",
      images[5] = "../../assets/imgs/qa-bg.jpg",
      images[6] = "./../assets/imgs/issue-bg.jpg";
    let elem = document.querySelectorAll('.workflow-button');
    if (elem != null) {
      Object.keys(elem).map((key) => {
        var randomCount = Math.round(Math.random() * (images.length - 1)) + 1;
        elem[key].style.backgroundImage = "url(" + images[randomCount] + ")";
        console.log("hey");
      });
    }
    console.log(elem.length);
  }


  determinePushPage(item: any) {
    this.navCtrl.push(item.pushLink);

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad StartWorkflowPage');
  }

  toggleTranslate(language: string) {

  }
  showIssueCat() {
    this.showIssueCatbool = !this.showIssueCatbool;

  }

  showGarmnetCat() {
    this.showGarmnetCatbool = !this.showGarmnetCatbool;
  }

  doOpenPage(pageName: string, parameter: string, businessTypeId: string) {
    if (pageName == "InitGarmentPage") {
      this.navCtrl.push(InitGarmentPage, { parameter: parameter, businessTypeId: businessTypeId });
    }
  }
  HidetabBar() {
    let elements = document.querySelectorAll(".tabbar");
    if (elements != null) {
      Object.keys(elements).map((key) => {
        elements[key].style.display = 'none';
      });
    }

  }

  getBusinessTypeList() {
    console.log("get Business Type list begin");
    let jsonFile = AppConfig.getBackEndUrl() + "/Inquiry/Search";
    let query = " query bq{Type(name:\"Garment\"){id,name,businessType(factoryCode:\""+ this.factoryCode +"\"){id,name,subBusinessType(factoryCode:\""+ this.factoryCode +"\"){id,name,pageName,parameter}}}}"
    let jsonDict = { "jsonFile": jsonFile, "pageIndex": 0, "pageSize": 0, body: query };
    this.httpclient.postData<any>(jsonDict).subscribe((itemGroup) => {
      if (itemGroup) {
        console.log(JSON.stringify(itemGroup.Type[0].businessType));
        this.workFlowOption = itemGroup.Type[0].businessType;
      }
      console.log("get Business Type list end!");
    }, (errMsg) => {
      console.log(errMsg);
    })

  }

}
