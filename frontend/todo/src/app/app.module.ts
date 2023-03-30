import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { API_URL } from './core/env.token';
import { environment } from 'src/environments/environment';

const routes = [
  {
    path: '',
    loadComponent: () => import('./features/todos/todo-list/todo-list.component')
  }
]

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  providers: [{
    provide: API_URL,
    useValue: environment.apiUrl
  }],
  bootstrap: [AppComponent]
})

export class AppModule {}
