import { Post } from './post';

describe('Post', () => {
  it('should create an instance', () => {
    expect(new Post("Hey how are you","ks8346",["nice","hey"],1)).toBeTruthy();
  });
});
