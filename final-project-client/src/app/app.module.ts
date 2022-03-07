import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { TeleService } from './tele.service';
import { CreateComponent } from './create/create.component';
import { DisplayComponent } from './display/display.component';

const appRoutes: Routes = [
  { path: '', component: CreateComponent},
  { path: 'display', component: DisplayComponent},
  { path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    CreateComponent,
    DisplayComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    HttpClientModule
  ],
  providers: [TeleService],
  bootstrap: [AppComponent]
})
export class AppModule { }
