import { Post } from './post';

describe('Post', () => {
  it('should create an instance', () => {
    expect(new Post("This is desc",1,[],"This is title",10,{id:1,name:"kartik",email:"kartik@gmail.com",teams:{id:1,name:"devs"}})).toBeTruthy();
  });
});
