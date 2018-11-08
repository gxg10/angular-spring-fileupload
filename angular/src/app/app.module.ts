import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {HttpClientModule} from '@angular/common/http';


import { AppComponent } from './app.component';
import { DetailsUploadComponent } from './details-upload/details-upload.component';
import { FormUploadComponent } from './form-upload/form-upload.component';
import { ListUploadComponent } from './list-upload/list-upload.component';

@NgModule({
  declarations: [
    AppComponent,
    DetailsUploadComponent,
    FormUploadComponent,
    ListUploadComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
