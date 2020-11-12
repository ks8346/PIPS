import { Comment } from './comment';

describe('Comment', () => {
  it('should create an instance', () => {
    expect(new Comment(1,"comment",new Date(),{id:1,name:"name"})).toBeTruthy();
  });
});
