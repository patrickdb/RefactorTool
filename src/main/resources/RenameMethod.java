interface SimpleInterface {
    public void MethodInInterface();
    public void MethodOverrideInInterface();
}

class Base {

    public void MethodOverride(){
        return;
    }

    public void MethodOverrideInInterface()
    {
        return;
    }

    public int MethodOverload(int b)
    {
        return b;
    }
}

class Middle extends Base {
      @Override
      public void MethodOverride(){
       }

       public void MethodOverrideInInterface()
       {
           return;
       }

       @Override
       public int MethodOverload()
    {
        return 0;
    }
}

class MyMethod extends Middle implements SimpleInterface
{
    public int MethodXYZ()
    {
        int a = 5;
        a = a+1;
        return a;
    }

    public void MethodInInterface()
    {
        return;
    }

    public void MethodOverrideInInterface()
    {
        return;
    }

    @Override
    public int MethodOverload()
    {
        return 2;
    }

    @Override
    public void MethodOverride()
    {
    }
}