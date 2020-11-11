import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentComponent } from './comment.component';

describe('CommentComponent', () => {
  let component: CommentComponent;
  let fixture: ComponentFixture<CommentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentComponent);
    component = fixture.componentInstance;
    component.comment = {
                              comment: 'this is a comment',
                              id: 7,
                              creationDate : new Date("12/11/202"),
                              user :{
                                id: 7,
                                name: 'Priyank'
                                      }
                        };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit when we delete a comment', () => {
    component.deleteComment()
    spyOn(component.parentFunction, 'emit').and.callThrough();
    
  });
});
