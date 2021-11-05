package cn.octautumn.cet6wordshelper.OnRunning;

public abstract class RunMode implements Runnable
{
    public int RunningStatus = 0; //0-Running 1-AnswerStop 2-TimeQuit 3-ErrorQuit 4-OtherStop/Quit
    public Timer timer;

    protected int getRandom(int start, int end)
    {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    public void exit()
    {
        RunningStatus = 4;
        synchronized (timer)
        {
            timer.notify();
        }
    }
}
