# CalculatorTest — Working agreement for Claude

## Commit after every small task, with a report

Break work into **small tasks**. As soon as a small task is done, commit its
changes — never batch several tasks into one commit. Each commit must carry a
**report of what you did** as its message.

## Every commit also saves the conversation to `story_claude/`

All commits go through the helper script. It writes a markdown "story" file of
the conversation and creates the commit in one step:

    powershell -ExecutionPolicy Bypass -File ./story_claude/commit_with_story.ps1 -Title "<short title>" -Report "<what you did>" -Prompt "<user's prompt>"

Run it from the repository root (the PowerShell tool is the natural choice).

Arguments:

- **-Title** — a short, abstract title for the conversation/task, **max 50
  characters**. Becomes the `shorttitle` in the filename and the file heading.
- **-Report** — your report of what you did in this task. Becomes the git
  commit message *and* the `## Report` section of the story file. Make it a
  meaningful account of the conversation/work, not a one-liner.
- **-Prompt** — the user's prompt for this task, **verbatim**. The helper
  appends it at the end of the story file under `## Prompt`.

The helper creates `story_claude/<yyMMdd_HHmmss>_<shorttitle>.md` and commits
that story file together with your changes.

## Hard rules

- **Never run `git commit` directly** — a `PreToolUse` hook
  (`.claude/settings.json`) blocks it. Always use `commit_with_story.ps1`.
- One story file and one commit per small task.
- To stage only specific paths, add `-Paths "<path1>","<path2>"`; otherwise the
  helper stages all current changes.
