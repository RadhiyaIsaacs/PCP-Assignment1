import pandas as pd
import matplotlib.pyplot as plt

# Load CSV
df = pd.read_csv("benchmark_results.csv")

# Get unique grid sizes
grid_sizes = sorted(df["GridSize"].unique())

plt.figure(figsize=(8,6))

# Plot each grid size on the same graph
for grid in grid_sizes:
    subset = df[df["GridSize"] == grid].sort_values("SearchFactor")
    plt.plot(subset["SearchFactor"], subset["Speedup"], 
             marker="o", label=f"Grid {grid}")

plt.title("Parallel Speedup vs Search Density")
plt.xlabel("Search Factor (Density)")
plt.ylabel("Speedup (Serial / Parallel)")
plt.legend(title="Grid Size", bbox_to_anchor=(1.05, 1), loc='upper left')
plt.grid(True)
plt.tight_layout()
plt.savefig("speedup_vs_density.png", bbox_inches='tight')
plt.show()
print("Graph saved as speedup_vs_density.png")
