import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { CreateSubComponent } from './create-sub/create-sub.component';
import { DisplaySubComponent } from './display-sub/display-sub.component';
import { TeleStripeService } from './tele-stripe.service';

const appRoutes: Routes = [
  { path: '', component: CreateSubComponent},
  { path: 'display', component: DisplaySubComponent},
  { path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    CreateSubComponent,
    DisplaySubComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    HttpClientModule
  ],
  providers: [TeleStripeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
