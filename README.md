Puzzle is a library which handles storing and retreiving data related to an Android Digital Audio Workstation. For more 
information, see [this blog post](http://blog.chrynan.com/2015/12/programming-android-daw-storage.html).

Example usage:

```Java
Puzzle.with(context).pieceTogether(project).then(new Callback(){
    public void onSuccess(Result result){
        Project p = (Project) result.getSingleResult();
    }
    public void onError(Error error){
    }
});
```
