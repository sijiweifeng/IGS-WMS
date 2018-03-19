import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { ScanGarmentPage } from '../pages/core/Receipt/Garment/scan-garment/scan-garment';
import { ConfirmLocationGarmentPage } from '../pages/core/Receipt/Garment/cfm-location-garment/cfm-location-garment';
import {ScanPage} from '../pages/core/Receipt/Fabric/scan/scan';
import {DetailPage} from '../pages/detail/detail';
import{BarcodeScanPage} from '../pages/barcode-scan/barcode-scan';
import {WorkflowcontrolPage} from '../pages/core/workflowcontrol/workflowcontrol';
import {PrintPage} from '../pages/print/print';
import {InitGarmentPage} from '../pages/core/Receipt/Garment/init-garment/init-garment';
import {QaissuePage} from "../pages/issue/qaissue/qaissue";
import {FnissuePage} from "../pages/issue/fnissue/fnissue";
import {RequestissuePage} from "../pages/issue/requestissue/requestissue";

import {TranslateModule} from '@ngx-translate/core';
import { SplashScreen } from '@ionic-native/splash-screen';
import {RequestPage} from '../pages/core/Receipt/Garment/request/request';
import {FabricRequestPage} from '../pages/core/Receipt/Fabric/fabricRequest/fabricRequest';
import { HTTP } from '@ionic-native/http';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import {EsqHttpClient} from "../providers/HttpClient";
import {JsonpModule, HttpModule} from "@angular/http";
import { NgCircleProgressModule } from 'ng-circle-progress';
import { StatusBar } from '@ionic-native/status-bar';
import { ProgressBarComponent } from '../components/progress-bar/progress-bar';
import { DataProvider } from '../providers/data/data';
import { RestProvider } from '../providers/rest/rest';
import { BarcodeScanner } from '@ionic-native/barcode-scanner';
import { Toast } from '@ionic-native/toast';
import { FileChooser } from '@ionic-native/file-chooser';


@NgModule({
  declarations: [
    MyApp,
    HomePage,
    ScanGarmentPage,
    ConfirmLocationGarmentPage,
    DetailPage,
    ScanPage,
    BarcodeScanPage,
    RequestPage,
    WorkflowcontrolPage,
    FabricRequestPage,
    PrintPage,
    ProgressBarComponent,
    InitGarmentPage,
    QaissuePage,
    FnissuePage,
    RequestissuePage
  ],
  imports: [
    TranslateModule.forRoot(),
    HttpModule,
    JsonpModule,
    BrowserModule,
    IonicModule.forRoot(MyApp),
    NgCircleProgressModule.forRoot
    (
      {
        radius: 100,
        outerStrokeWidth: 16,
        innerStrokeWidth: 8,
        outerStrokeColor: "#78C000",
        innerStrokeColor: "#C7E596",
        animationDuration: 300,
      }
    )

  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    ScanGarmentPage,
    ConfirmLocationGarmentPage,
    DetailPage,
    ScanPage,
    BarcodeScanPage,
    RequestPage,
    WorkflowcontrolPage,
    FabricRequestPage,
    PrintPage,
    InitGarmentPage,
    QaissuePage,
    FnissuePage,
    RequestissuePage
  ],
  providers: [
    FileChooser,
    StatusBar,
    SplashScreen,
    BarcodeScanner,
    Toast,
    HTTP,
    HttpClient,
    EsqHttpClient,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    DataProvider,
    RestProvider
  ]
}
)
export class AppModule {}
