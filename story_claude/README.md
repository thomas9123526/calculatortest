# story_claude helper

This helper keeps a conversation story file for each task and commits repository changes with a report.

## Usage

Run from the repository root in PowerShell:

```powershell
pwsh .\story_claude\commit_with_story.ps1 \
  -Title "Short title of task" \
  -Report "What I have done after this small task." \
  -Prompt "<your prompt appended at the end>"
```

## Behavior

- Creates `story_claude/yyMMdd_HHmmss_shorttitle.md`
- Saves the report and prompt inside the markdown file
- Stages the new story file and the specified paths
- Commits with the report as the commit message

## Notes

- `shorttitle` is sanitized and limited to 50 characters.
- If no `-Paths` are provided, it stages the current directory (`.`) by default.
