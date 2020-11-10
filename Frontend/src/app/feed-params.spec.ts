import { FeedParams } from './feed-params';

describe('FeedParams', () => {
  it('should create an instance', () => {
    expect(new FeedParams(new Date(),new Date(),"1","3")).toBeTruthy();
  });
});
