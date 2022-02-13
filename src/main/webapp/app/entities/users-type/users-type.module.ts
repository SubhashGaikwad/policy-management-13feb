import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UsersTypeComponent } from './list/users-type.component';
import { UsersTypeDetailComponent } from './detail/users-type-detail.component';
import { UsersTypeUpdateComponent } from './update/users-type-update.component';
import { UsersTypeDeleteDialogComponent } from './delete/users-type-delete-dialog.component';
import { UsersTypeRoutingModule } from './route/users-type-routing.module';

@NgModule({
  imports: [SharedModule, UsersTypeRoutingModule],
  declarations: [UsersTypeComponent, UsersTypeDetailComponent, UsersTypeUpdateComponent, UsersTypeDeleteDialogComponent],
  entryComponents: [UsersTypeDeleteDialogComponent],
})
export class UsersTypeModule {}
